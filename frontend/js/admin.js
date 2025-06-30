
// document.addEventListener('DOMContentLoaded', () => {
//   document.getElementById('flaggedPosts').innerText = 'No flagged posts (backend coming!)';
// });
document.addEventListener('DOMContentLoaded', () => {
  fetch("/api/journal/flagged", {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    },
    credentials: "include" // include OAuth2 session cookie
  })
  .then(response => {
    if (!response.ok) throw new Error("Unauthorized or server error");
    return response.json();
  })
  .then(data => {
    const flaggedPosts = document.getElementById("flaggedPosts");
    flaggedPosts.innerHTML = "";

    if (data.length === 0) {
      flaggedPosts.innerText = "No flagged journal entries found.";
      return;
    }

    data.forEach(entry => {
      const div = document.createElement("div");
      div.className = "entry";
      div.innerHTML = `
        <p><strong>Entry:</strong> ${entry.content}</p>
        <p><strong>Sentiment:</strong> ${entry.sentiment}</p>
        <p><strong>Flagged:</strong> ${entry.isFlagged}</p>
        <p><strong>Public:</strong> ${entry.isPublic}</p>
        <hr/>
      `;
      flaggedPosts.appendChild(div);
    });
  })
  .catch(error => {
    document.getElementById("flaggedPosts").innerText = "Error loading flagged entries. (Are you admin?)";
    console.error("Fetch error:", error);
  });
});
