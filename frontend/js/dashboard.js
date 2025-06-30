document.addEventListener("DOMContentLoaded", () => {
  // ✅ Fetch quote from your own backend (Spring Boot proxy)
  fetch("http://localhost:8080/api/quotes/today", {
    credentials: "include"
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("quoteBox").innerText = `${data[0].q} — ${data[0].a}`;
    })
    .catch(err => {
      document.getElementById("quoteBox").innerText = "Stay positive. You're not alone 💙";
      console.error("Quote fetch failed:", err);
    });

  const form = document.getElementById("journalForm");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const entryText = form.entry.value.trim();
    const isPublic = form.isPublic.checked;

    if (!entryText) {
      document.getElementById("responseMsg").innerText = "Please enter something before submitting.";
      return;
    }

    const payload = {
      content: entryText,
      isPublic: isPublic
    };

    try {
      // ✅ Submit as authenticated user
      const response = await fetch("http://localhost:8080/api/journal/submit", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include", // Send OAuth2 cookie
        body: JSON.stringify(payload)
      });

      if (response.status === 401 || response.status === 403) {
        throw new Error("Not authenticated");
      }

      const result = await response.text();
      document.getElementById("responseMsg").innerText = result;
      form.reset();
    } catch (err) {
      // ✅ Fallback to anonymous submission
      try {
        const anonResponse = await fetch("http://localhost:8080/api/journal/anonymous", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(payload)
        });

        const anonResult = await anonResponse.text();
        document.getElementById("responseMsg").innerText = "(Anonymous) " + anonResult;
        form.reset();
      } catch (error) {
        console.error("Anonymous submission failed:", error);
        document.getElementById("responseMsg").innerText = "Failed to submit entry.";
      }
    }
  });
});
