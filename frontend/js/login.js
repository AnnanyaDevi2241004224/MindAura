
// document.getElementById('loginForm').addEventListener('submit', function(e) {
//   e.preventDefault();
//   const form = e.target;
//   const data = { username: form.username.value, password: form.password.value };
//   alert('Login simulated: ' + JSON.stringify(data));
// });
// document.getElementById('loginForm').addEventListener('submit', function(e) {
//   e.preventDefault();

//   const form = e.target;
//   const data = {
//     username: form.username.value,
//     password: form.password.value
//   };

//   fetch('http://localhost:8080/api/auth/login', {  // update URL if needed
//     method: 'POST',
//     headers: {
//       'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(data)
//   })
//   .then(res => {
//     if (!res.ok) throw new Error('Login failed');
//     return res.json();
//   })
//   .then(response => {
//     localStorage.setItem('jwt', response.token);  // Save JWT for later
//     window.location.href = 'dashboard.html';      // Redirect to dashboard
//   })
//   .catch(err => {
//     alert('Login failed. Please check credentials.');
//     console.error(err);
//   });
// });

