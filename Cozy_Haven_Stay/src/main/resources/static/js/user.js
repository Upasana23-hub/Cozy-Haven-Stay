// ============================================================
// user.js — User Dashboard & Profile Logic
// Reads logged-in user from sessionStorage (set by auth.js on login)
// Fetches live data from backend by userId
// ============================================================

// ── Get the logged-in user from session ──
function getSessionUser() {
    const raw = sessionStorage.getItem('user');
    return raw ? JSON.parse(raw) : null;
}

// ── Redirect to login if not logged in ──
function requireLogin() {
    const user = getSessionUser();
    if (!user) {
        sessionStorage.setItem('redirectAfterLogin', window.location.href);
        window.location.href = 'login.html';
        return null;
    }
    return user;
}

// ============================================================
// USER DASHBOARD
// ============================================================
async function initUserDashboard() {
    const user = requireLogin();
    if (!user) return;

    // Fill sidebar profile name
    const sidebarName = document.getElementById('sidebarUserName');
    const sidebarRole = document.getElementById('sidebarUserRole');
    if (sidebarName) sidebarName.textContent = user.firstName || user.name || user.email || 'User';
    if (sidebarRole) sidebarRole.textContent = user.role ? user.role.replace('ROLE_', '') : 'User';

    // Fill welcome bar
    const welcomeName = document.getElementById('welcomeName');
    if (welcomeName) welcomeName.textContent = user.firstName || user.name || 'User';

    // Load booking count for this user
    const userId = user.userId || user.id;
    await loadUserStats(userId);

    // Logout button in sidebar
    const logoutSidebar = document.getElementById('sidebarLogout');
    if (logoutSidebar) {
        logoutSidebar.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                await fetch('http://localhost:8080/logout', { method: 'POST', credentials: 'include' });
            } catch (_) { }
            sessionStorage.removeItem('user');
            window.location.href = 'index.html';
        });
    }
}

async function loadUserStats(userId) {
    // Fetch bookings
    const bookingCountEl = document.getElementById('statBookings');
    try {
        const res = await fetch(`http://localhost:8080/api/bookings/user/${userId}`, { credentials: 'include' });
        if (res.ok) {
            const bookings = await res.json();
            if (bookingCountEl) bookingCountEl.textContent = Array.isArray(bookings) ? bookings.length : 0;
        } else {
            if (bookingCountEl) bookingCountEl.textContent = '0';
        }
    } catch {
        if (bookingCountEl) bookingCountEl.textContent = '0';
    }

    // Fetch total hotels count (available to all)
    const hotelCountEl = document.getElementById('statHotels');
    try {
        const res2 = await fetch('http://localhost:8080/api/hotels/getall', { credentials: 'include' });
        if (res2.ok) {
            const hotels = await res2.json();
            if (hotelCountEl) hotelCountEl.textContent = Array.isArray(hotels) ? hotels.length : '–';
        } else {
            if (hotelCountEl) hotelCountEl.textContent = '–';
        }
    } catch {
        if (hotelCountEl) hotelCountEl.textContent = '–';
    }
}

// ============================================================
// USER PROFILE
// ============================================================
async function initUserProfile() {
    const user = requireLogin();
    if (!user) return;

    const userId = user.userId || user.id;

    // Set placeholders immediately from session
    fillProfileUI(user);

    // Then fetch fresh data from backend
    try {
        const res = await fetch(`http://localhost:8080/api/users/getbyid/${userId}`, { credentials: 'include' });
        if (res.ok) {
            const freshUser = await res.json();
            // Update session with latest
            sessionStorage.setItem('user', JSON.stringify({ ...user, ...freshUser }));
            fillProfileUI(freshUser);
        }
    } catch (err) {
        console.warn('Could not refresh user data:', err.message);
        // Already showing session data, fine to continue
    }

    // Edit profile button
    const editBtn = document.getElementById('editProfileBtn');
    if (editBtn) {
        editBtn.addEventListener('click', () => {
            const fields = document.querySelectorAll('.profile-field');
            fields.forEach(f => f.removeAttribute('disabled'));
            document.getElementById('saveProfileBtn').style.display = 'inline-block';
            editBtn.style.display = 'none';
        });
    }

    // Save profile button
    const saveBtn = document.getElementById('saveProfileBtn');
    if (saveBtn) {
        saveBtn.addEventListener('click', async () => {
            const firstName = document.getElementById('profileFirstName')?.value.trim();
            const lastName = document.getElementById('profileLastName')?.value.trim();
            const phone = document.getElementById('profilePhone')?.value.trim();
            const email = document.getElementById('profileEmail')?.value.trim();

            const payload = { firstName, lastName, phoneNumber: phone, email };

            try {
                const res = await fetch(`http://localhost:8080/api/users/update/${userId}`, {
                    method: 'PUT',
                    credentials: 'include',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                if (res.ok) {
                    const updated = await res.json();
                    sessionStorage.setItem('user', JSON.stringify({ ...user, ...updated }));
                    alert('Profile updated successfully!');
                    // Disable fields again
                    document.querySelectorAll('.profile-field').forEach(f => f.setAttribute('disabled', true));
                    saveBtn.style.display = 'none';
                    editBtn.style.display = 'inline-block';
                } else {
                    alert('Failed to update profile.');
                }
            } catch {
                alert('Error saving profile. Please try again.');
            }
        });
    }

    // Logout
    const logoutBtn = document.getElementById('profileLogout');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                await fetch('http://localhost:8080/logout', { method: 'POST', credentials: 'include' });
            } catch (_) { }
            sessionStorage.removeItem('user');
            window.location.href = 'index.html';
        });
    }
}

function fillProfileUI(user) {
    const fullName = [user.firstName, user.lastName].filter(Boolean).join(' ') || user.name || 'User';

    const profileNameBig = document.getElementById('profileNameBig');
    if (profileNameBig) profileNameBig.textContent = fullName;

    const profileFirstName = document.getElementById('profileFirstName');
    if (profileFirstName) profileFirstName.value = user.firstName || '';

    const profileLastName = document.getElementById('profileLastName');
    if (profileLastName) profileLastName.value = user.lastName || '';

    const profileEmail = document.getElementById('profileEmail');
    if (profileEmail) profileEmail.value = user.email || '';

    const profilePhone = document.getElementById('profilePhone');
    if (profilePhone) profilePhone.value = user.phoneNumber || user.phone || '';

    const profileRole = document.getElementById('profileRole');
    if (profileRole) profileRole.value = (user.role || '').replace('ROLE_', '');
}