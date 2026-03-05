// ==========================================
// api.js — Session-Based Auth (Spring Security)
// No JWT. Browser sends JSESSIONID cookie automatically.
// ==========================================

const API_BASE_URL = 'http://localhost:8080/api';

const API = {

    // ---- User helper (stored in sessionStorage after login) ----
    getUser: () => {
        const u = sessionStorage.getItem('user');
        return u ? JSON.parse(u) : null;
    },

    setUser: (userObj) => {
        sessionStorage.setItem('user', JSON.stringify(userObj));
    },

    clearUser: () => {
        sessionStorage.removeItem('user');
    },

    // ---- Default headers (NO Authorization header — session cookie handles auth) ----
    headers: () => ({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }),

    // ---- Response handler ----
	handleResponse: async (response) => {
	    const contentType = response.headers.get('content-type') || '';
	    if (contentType.includes('application/json')) {
	        const data = await response.json();
	        if (!response.ok) throw new Error(data.message || `Error ${response.status}`);
	        return data;
	    } else {
	        // Handle plain text safely
	        const text = await response.text();
	        if (!response.ok) {
	            // Wrap plain text in Error
	            throw new Error(text || `Error ${response.status}`);
	        }
	        return text;
	    }
	},

    // ---- HTTP Methods — all include credentials so the JSESSIONID cookie is sent ----

    get: async (path) => {
        const res = await fetch(`${API_BASE_URL}${path}`, {
            method: 'GET',
            headers: API.headers(),
            credentials: 'include'   // sends session cookie
        });
        return API.handleResponse(res);
    },

    post: async (path, body) => {
        const res = await fetch(`${API_BASE_URL}${path}`, {
            method: 'POST',
            headers: API.headers(),
            credentials: 'include',
            body: JSON.stringify(body)
        });
        return API.handleResponse(res);
    },

    put: async (path, body) => {
        const res = await fetch(`${API_BASE_URL}${path}`, {
            method: 'PUT',
            headers: API.headers(),
            credentials: 'include',
            body: JSON.stringify(body)
        });
        return API.handleResponse(res);
    },

    patch: async (path, body) => {
        const res = await fetch(`${API_BASE_URL}${path}`, {
            method: 'PATCH',
            headers: API.headers(),
            credentials: 'include',
            body: body ? JSON.stringify(body) : undefined
        });
        return API.handleResponse(res);
    },

    delete: async (path) => {
        const res = await fetch(`${API_BASE_URL}${path}`, {
            method: 'DELETE',
            headers: API.headers(),
            credentials: 'include'
        });
        if (res.status === 204) return null;
        return API.handleResponse(res);
    }
};