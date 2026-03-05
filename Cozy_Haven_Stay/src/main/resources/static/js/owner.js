// ============================================================
// owner.js — Cozy Haven Stay Owner Dashboard
// Uses session-based auth (credentials: 'include')
// ============================================================

const OWNER_API = 'http://localhost:8080/api';

// ── Helper: session-authenticated fetch ──
async function ownerFetch(path, options = {}) {
    const res = await fetch(`${OWNER_API}${path}`, {
        ...options,
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', ...(options.headers || {}) }
    });
    if (!res.ok) throw new Error(`Error ${res.status}`);
    if (res.status === 204) return null;
    return res.json();
}

// ── Get current user from sessionStorage ──
function getCurrentUser() {
    return JSON.parse(sessionStorage.getItem('user') || 'null');
}

// ── Guard: redirect if not owner ──
function guardOwner() {
    const user = getCurrentUser();
    if (!user) { window.location.href = 'login.html'; return null; }
    const role = (user.role ?? '').replace('ROLE_', '');
    if (role !== 'OWNER' && role !== 'ADMIN') { window.location.href = 'index.html'; return null; }
    return user;
}

// ── Set owner name in sidebar ──
function setOwnerName(user) {
    const el = document.getElementById('ownerName');
    if (el) el.textContent = (user.firstName ?? '') + ' ' + (user.lastName ?? 'Owner');
}

// ── Load Stats ──
async function loadStats(ownerId) {
    try {
        const [rooms, bookings] = await Promise.all([
            ownerFetch('/rooms/getall'),
            ownerFetch('/bookings/getall')
        ]);

        document.getElementById('totalRooms').innerText = rooms?.length ?? 0;
        document.getElementById('totalBookings').innerText = bookings?.length ?? 0;

        let revenue = 0;
        (bookings ?? []).forEach(b => { if (b.totalPrice) revenue += b.totalPrice; });
        document.getElementById('totalRevenue').innerText = '₹' + revenue.toLocaleString('en-IN');

    } catch (err) {
        console.error('Stats error:', err);
    }
}

// ── Load Rooms Table ──
async function loadRooms() {
    const table = document.getElementById('roomsTable');
    try {
        const rooms = await ownerFetch('/rooms/getall');
        table.innerHTML = !rooms || rooms.length === 0
            ? '<tr><td colspan="6" class="text-center text-muted py-3">No rooms added yet</td></tr>'
            : rooms.map(r => `
                <tr>
                    <td>${r.roomId ?? r.id ?? '-'}</td>
                    <td>${r.roomSize ?? r.roomType ?? '-'}</td>
                    <td>${r.bedType ?? '-'}</td>
                    <td>${r.maxOccupancy ?? r.capacity ?? '-'}</td>
                    <td>₹${r.baseFare ?? r.price ?? '-'}</td>
                    <td>
                        <button class="act-btn del" onclick="deleteRoom(${r.roomId ?? r.id})">
                            <i class="fa fa-trash me-1"></i>Delete
                        </button>
                    </td>
                </tr>`).join('');
    } catch (err) {
        table.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Failed to load rooms</td></tr>';
        console.error('loadRooms error:', err);
    }
}

// ── Delete Room ──
async function deleteRoom(id) {
    if (!confirm('Delete this room?')) return;
    try {
        await ownerFetch(`/rooms/delete/${id}`, { method: 'DELETE' });
        loadRooms();
        loadStats();
    } catch (err) {
        alert('Delete failed: ' + err.message);
    }
}

// ── Load Bookings Table ──
async function loadBookings() {
    const table = document.getElementById('bookingsTable');
    try {
        const bookings = await ownerFetch('/bookings/getall');
        table.innerHTML = !bookings || bookings.length === 0
            ? '<tr><td colspan="6" class="text-center text-muted py-3">No bookings yet</td></tr>'
            : bookings.map(b => `
                <tr>
                    <td>${b.userId ?? '-'}</td>
                    <td>${b.roomId ?? '-'}</td>
                    <td>${b.checkInDate ?? '-'}</td>
                    <td>${b.checkOutDate ?? '-'}</td>
                    <td><span class="bstatus ${b.bookingStatus}">${b.bookingStatus ?? '-'}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger rounded-pill"
                            onclick="cancelBooking(${b.bookingId ?? b.id})">
                            Cancel
                        </button>
                    </td>
                </tr>`).join('');
    } catch (err) {
        table.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Failed to load bookings</td></tr>';
        console.error('loadBookings error:', err);
    }
}

// ── Cancel Booking ──
async function cancelBooking(id) {
    if (!confirm('Cancel this booking?')) return;
    try {
        await ownerFetch(`/bookings/cancel/${id}`, { method: 'DELETE' });
        loadBookings();
        loadStats();
    } catch (err) {
        alert('Cancel failed: ' + err.message);
    }
}

// ── Logout ──
function setupLogout() {
    const logoutEl = document.querySelector('.dash-nav a:last-child');
    if (logoutEl) {
        logoutEl.addEventListener('click', async (e) => {
            e.preventDefault();
            await fetch('http://localhost:8080/logout', { method: 'POST', credentials: 'include' });
            sessionStorage.clear();
            window.location.href = 'login.html';
        });
    }
}

// ── Init ──
document.addEventListener('DOMContentLoaded', () => {
    const user = guardOwner();
    if (!user) return;
    setOwnerName(user);
    loadStats();
    loadRooms();
    loadBookings();
    setupLogout();
});