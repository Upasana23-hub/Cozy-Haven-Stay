// ============================================================
// admin.js — Cozy Haven Stay Admin Dashboard
// Uses session-based auth (credentials: 'include')
// ============================================================

const ADMIN_API = 'http://localhost:8080/api';

// ── Helper: session-authenticated fetch ──
async function adminFetch(path, options = {}) {
    const res = await fetch(`${ADMIN_API}${path}`, {
        ...options,
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', ...(options.headers || {}) }
    });
    if (!res.ok) throw new Error(`Error ${res.status}`);
    if (res.status === 204) return null;
    return res.json();
}

// ── Load Stats ──
async function loadStats() {
    try {
        const [users, bookings, hotels] = await Promise.all([
            adminFetch('/admin/users'),
            adminFetch('/admin/bookings'),
            adminFetch('/hotels/getall')
        ]);
        document.getElementById('totalUsers').innerText = users?.length ?? 0;
        document.getElementById('totalBookings').innerText = bookings?.length ?? 0;
        document.getElementById('totalHotels').innerText = hotels?.length ?? 0;
    } catch (err) {
        console.error('Stats error:', err);
    }
}

// ── Load Users Table ──
async function loadUsers() {
    const table = document.getElementById('usersTable');
    try {
        const users = await adminFetch('/admin/users');
        table.innerHTML = users.length === 0
            ? '<tr><td colspan="5" class="text-center text-muted py-3">No users found</td></tr>'
            : users.map(u => `
                <tr>
                    <td>${u.userId ?? u.id ?? '-'}</td>
                    <td>${(u.firstName ?? '') + ' ' + (u.lastName ?? '')}</td>
                    <td>${u.email ?? '-'}</td>
                    <td><span class="badge" style="background:var(--pink); border-radius:99px;">${(u.role ?? 'USER').replace('ROLE_', '')}</span></td>
                    <td>
                        <button class="act-btn del" onclick="deleteUser(${u.userId ?? u.id})">
                            <i class="fa fa-trash me-1"></i>Delete
                        </button>
                    </td>
                </tr>`).join('');
    } catch (err) {
        table.innerHTML = '<tr><td colspan="5" class="text-center text-danger">Failed to load users</td></tr>';
        console.error('loadUsers error:', err);
    }
}

// ── Delete User ──
async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) return;
    try {
        await adminFetch(`/admin/users/${id}`, { method: 'DELETE' });
        loadUsers();
        loadStats();
    } catch (err) {
        alert('Delete failed: ' + err.message);
    }
}

// ── Load All Bookings ──
async function loadBookings() {
    const table = document.getElementById('adminBookingsTable');
    try {
        const bookings = await adminFetch('/admin/bookings');
        table.innerHTML = bookings.length === 0
            ? '<tr><td colspan="6" class="text-center text-muted py-3">No bookings found</td></tr>'
            : bookings.map(b => `
                <tr>
                    <td>${b.userId ?? '-'}</td>
                    <td>${b.hotelId ?? '-'}</td>
                    <td>${b.roomId ?? '-'}</td>
                    <td>${b.checkInDate ?? '-'}</td>
                    <td><span class="bstatus ${b.bookingStatus}">${b.bookingStatus ?? '-'}</span></td>
                    <td>
                        <button class="act-btn del" onclick="cancelBooking(${b.bookingId ?? b.id})">
                            <i class="fa fa-times me-1"></i>Cancel
                        </button>
                    </td>
                </tr>`).join('');
    } catch (err) {
        table.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Failed to load bookings</td></tr>';
        console.error('loadBookings error:', err);
    }
}

// ── Cancel Booking (Admin) ──
async function cancelBooking(id) {
    if (!confirm('Cancel this booking?')) return;
    try {
        await adminFetch(`/admin/bookings/${id}/cancel`, { method: 'PUT' });
        loadBookings();
        loadStats();
    } catch (err) {
        alert('Cancel failed: ' + err.message);
    }
}

// ── Set Admin Name ──
function setAdminName() {
    const user = JSON.parse(sessionStorage.getItem('user') || '{}');
    const el = document.getElementById('adminName');
    if (el) el.textContent = (user.firstName ?? '') + ' ' + (user.lastName ?? 'Admin');
}

// ── Guard: redirect if not admin ──
function guardAdmin() {
    const user = JSON.parse(sessionStorage.getItem('user') || 'null');
    if (!user) { window.location.href = 'login.html'; return false; }
    const role = (user.role ?? '').replace('ROLE_', '');
    if (role !== 'ADMIN') { window.location.href = 'index.html'; return false; }
    return true;
}

// ── Logout ──
function setupLogout() {
    document.querySelector('[href="#"]')?.addEventListener('click', async (e) => {
        if (e.target.textContent.trim() === 'Logout') {
            e.preventDefault();
            await fetch('http://localhost:8080/logout', { method: 'POST', credentials: 'include' });
            sessionStorage.clear();
            window.location.href = 'login.html';
        }
    });
}

// ── Init ──
document.addEventListener('DOMContentLoaded', () => {
    if (!guardAdmin()) return;
    setAdminName();
    loadStats();
    loadUsers();
    loadBookings();
    setupLogout();
});