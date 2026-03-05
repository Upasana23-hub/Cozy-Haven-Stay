// ============================================================
// booking.js — Cozy Haven Stay Booking Page
// Uses session-based auth (credentials: 'include')
// ============================================================

const BOOK_API = 'http://localhost:8080/api';

// ── Read URL params ──
const urlParams = new URLSearchParams(window.location.search);
const roomId = urlParams.get('roomId');
const roomName = urlParams.get('room');
const bedType = urlParams.get('bed');
const roomPrice = urlParams.get('price');

// ── Fill UI with room info ──
function fillRoom(room) {
    const nameEl = document.getElementById('roomName');
    const bedEl = document.getElementById('bedType');
    const priceEl = document.getElementById('roomPrice');
    if (nameEl) nameEl.innerText = `Book ${room.roomType ?? room.roomSize ?? room.name ?? roomName ?? ''} Room`;
    if (bedEl) bedEl.innerText = room.bedType ?? bedType ?? '';
    if (priceEl) priceEl.innerText = room.baseFare ?? room.price ?? roomPrice ?? '';
}

// ── Fetch room details from backend if roomId given ──
async function initRoom() {
    if (roomId) {
        try {
            const res = await fetch(`${BOOK_API}/rooms/getbyid/${roomId}`, { credentials: 'include' });
            const room = await res.json();
            fillRoom(room);
        } catch (err) {
            console.error('Room fetch failed:', err);
            fillRoom({ roomType: roomName, bedType, baseFare: roomPrice });
        }
    } else {
        fillRoom({ roomType: roomName, bedType, baseFare: roomPrice });
    }
}

// ── Handle Booking Form Submit ──
function initBookingForm() {
    const form = document.getElementById('bookingForm') || document.querySelector('form');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Check login
        const user = JSON.parse(sessionStorage.getItem('user') || 'null');
        if (!user) {
            alert('Please sign in to make a booking.');
            window.location.href = 'login.html';
            return;
        }

        const checkIn = form.querySelector('#checkIn,  input[type="date"]:nth-of-type(1)')?.value;
        const checkOut = form.querySelector('#checkOut, input[type="date"]:nth-of-type(2)')?.value;

        if (!checkIn || !checkOut) {
            alert('Please select both check-in and check-out dates.');
            return;
        }

        if (new Date(checkOut) <= new Date(checkIn)) {
            alert('Check-out date must be after check-in date.');
            return;
        }

        const bookingPayload = {
            userId: user.userId ?? user.id,
            roomId: roomId ?? null,
            checkInDate: checkIn,
            checkOutDate: checkOut,
            bookingStatus: 'CONFIRMED'
        };

        try {
            const res = await fetch(`${BOOK_API}/bookings/create`, {
                method: 'POST',
                credentials: 'include',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(bookingPayload)
            });

            if (!res.ok) {
                const err = await res.text();
                throw new Error(err || 'Booking failed');
            }

            // Show success UI
            const successMsg = document.getElementById('bookingSuccess');
            if (successMsg) {
                successMsg.style.display = 'block';
            } else {
                alert('✅ Booking Confirmed! Redirecting to My Bookings...');
            }

            setTimeout(() => {
                window.location.href = 'my-bookings.html';
            }, 1800);

        } catch (err) {
            alert('Booking failed: ' + err.message);
            console.error(err);
        }
    });
}

// ── Init ──
document.addEventListener('DOMContentLoaded', () => {
    initRoom();
    initBookingForm();
});