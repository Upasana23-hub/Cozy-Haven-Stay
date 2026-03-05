// ==========================================
// auth.js — Session-Based Auth (Spring Security)
// Login stores user info in sessionStorage.
// Browser handles JSESSIONID cookie automatically.
// ==========================================

// ================== NAVBAR ==================
function initNavbar() {
    const user = API.getUser();

    const navGuest = document.getElementById('navGuest');
    const navGuest2 = document.getElementById('navGuest2');
    const navUser = document.getElementById('navUser');
    const navBookings = document.getElementById('navBookings');

    if (!user) {
        if (navGuest) navGuest.style.display = '';
        if (navGuest2) navGuest2.style.display = '';
        if (navUser) navUser.style.display = 'none';
        if (navBookings) navBookings.style.display = 'none';
        return;
    }

    // User is logged in
    if (navGuest) navGuest.style.display = 'none';
    if (navGuest2) navGuest2.style.display = 'none';
    if (navUser) navUser.style.display = '';

    const nameEl = document.getElementById('navUserName');
    if (nameEl) nameEl.textContent = user.firstName || user.name || user.email || 'User';

    const dashLink = document.getElementById('dashLinkDrop');
    if (dashLink) {
        const role = (user.role || '').replace('ROLE_', '');
        if (role === 'ADMIN') {
            dashLink.href = 'admin-dashboard.html';
        } else if (role === 'OWNER') {
            dashLink.href = 'owner-dashboard.html';
        } else {
            dashLink.href = 'user-dashboard.html';
            if (navBookings) navBookings.style.display = '';
        }
    }

    // Logout — POST to Spring's built-in logout endpoint
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                await fetch('http://localhost:8080/logout', {
                    method: 'POST',
                    credentials: 'include'
                });
            } catch (_) { }
            API.clearUser();
            window.location.href = 'index.html';
        });
    }
}

// ================== LOGIN PAGE ==================
function initLoginPage() {
    const pwdToggle = document.getElementById('togglePwd');
    if (pwdToggle) {
        pwdToggle.addEventListener('click', () => {
            const input = document.getElementById('password');
            const icon = pwdToggle.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'fa fa-eye-slash';
            } else {
                input.type = 'password';
                icon.className = 'fa fa-eye';
            }
        });
    }

    const form = document.getElementById('loginForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const errAlert = document.getElementById('loginErr');
        if (errAlert) errAlert.style.display = 'none';

        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        if (!email || !password) {
            showErr(errAlert, 'Please enter email and password');
            return;
        }

        try {
            const userRes = await API.post('/users/login', { email, password });
            API.setUser(userRes);

            const redirectTo = sessionStorage.getItem('redirectAfterLogin') || 'index.html';
            sessionStorage.removeItem('redirectAfterLogin');

            const role = (userRes.role || '').replace('ROLE_', '');
            if (role === 'ADMIN') {
                window.location.href = 'admin-dashboard.html';
            } else if (role === 'OWNER') {
                window.location.href = 'owner-dashboard.html';
            } else {
                window.location.href = redirectTo;
            }

        } catch (err) {
            showErr(errAlert, err.message || 'Invalid email or password');
        }
    });
}

// ================== REGISTER PAGE ==================
function initRegisterPage() {
    // Password toggle
    const pwdToggle = document.getElementById('togglePwd');
    if (pwdToggle) {
        pwdToggle.addEventListener('click', () => {
            const input = document.getElementById('password');
            const icon = pwdToggle.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'fa fa-eye-slash';
            } else {
                input.type = 'password';
                icon.className = 'fa fa-eye';
            }
        });
    }

    const pwdConfirmToggle = document.getElementById('toggleConfirm');
    if (pwdConfirmToggle) {
        pwdConfirmToggle.addEventListener('click', () => {
            const input = document.getElementById('confirmPassword');
            const icon = pwdConfirmToggle.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'fa fa-eye-slash';
            } else {
                input.type = 'password';
                icon.className = 'fa fa-eye';
            }
        });
    }

    const form = document.getElementById('registerForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const errAlert = document.getElementById('registerErr');
        const okAlert = document.getElementById('registerOk');
        if (errAlert) errAlert.style.display = 'none';
        if (okAlert) okAlert.style.display = 'none';

        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value.trim();
        const phone = document.getElementById('phone')?.value.trim() || '';
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Get role from radio buttons
        let role = 'USER';
        document.getElementsByName('role').forEach(r => {
            if (r.checked) role = r.value;
        });

        if (!name || !email || !password) {
            showErr(errAlert, 'Please fill all required fields');
            return;
        }
        if (password !== confirmPassword) {
            showErr(errAlert, 'Passwords do not match');
            return;
        }

        try {
            // STEP 1: Register — payload matches backend DTO
            const userPayload = {
                name: name,              // Full name
                email: email,
                phone: phone,
                password: password,
                role: 'ROLE_' + role     // ROLE_USER / ROLE_OWNER / ROLE_ADMIN
            };

            const userRes = await API.post('/users/register', userPayload);
            const savedUserId = userRes.userId || userRes.id;

            // STEP 2: Auto-login to establish session
            const loginRes = await API.post('/users/login', { email, password });
            API.setUser(loginRes);

            // STEP 3: If OWNER, create HotelOwner record
            if (role === 'OWNER') {
                const ownerPayload = {
                    buisnessName: name + ' Hotels',
                    address: 'To be updated',
                    gstNumber: 'PENDING',
                    userId: savedUserId
                };
                try {
                    await API.post('/owners/add', ownerPayload);
                } catch (ownerErr) {
                    console.warn('Owner profile creation failed:', ownerErr.message);
                }
            }

            if (okAlert) {
                okAlert.innerText = 'Registration successful! Redirecting...';
                okAlert.style.display = 'block';
            }

            setTimeout(() => {
                const r = (loginRes.role || '').replace('ROLE_', '');
                if (r === 'ADMIN') window.location.href = 'admin-dashboard.html';
                else if (r === 'OWNER') window.location.href = 'owner-dashboard.html';
                else window.location.href = 'index.html';
            }, 1500);

        } catch (err) {
            showErr(errAlert, err.message || 'Registration failed. Please try again.');
        }
    });
}

// ================== UTILITY ==================
function showErr(el, msg) {
    if (!el) return;
    el.innerText = msg;
    el.style.display = 'block';
}