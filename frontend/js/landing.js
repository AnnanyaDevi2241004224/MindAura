function showInstructions() {
  const box = document.getElementById('infoBox');
  box.innerHTML = "<h3>How to Use MindAura</h3><ul><li>Log in or continue anonymously.</li><li>Write journal entries daily.</li><li>View motivational quotes.</li><li>Admins moderate public posts.</li></ul>";
  box.style.display = "block";
}

function showCreator() {
  const box = document.getElementById('infoBox');
  box.innerHTML = "<h3>About the Creator</h3><p>This app was crafted with 💙 by someone who believes mental health matters. You're not alone on your journey.</p>";
  box.style.display = "block";
}