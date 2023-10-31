document.getElementById("show-image-button").addEventListener("click", function () {
    // Make an AJAX request to the Spring Controller to fetch the image
    fetch("http://localhost:8080/image")
        .then(response => response.blob())
        .then(imageBlob => {
            const imageUrl = URL.createObjectURL(imageBlob);
            const imageContainer = document.getElementById("image-container");
            const imageElement = document.createElement("img");
            imageElement.src = imageUrl;
            imageContainer.appendChild(imageElement);
        })
        .catch(error => {
            console.error("Error fetching image: " + error);
        })
});
document.getElementById("send-email-button").addEventListener("click", function () {
    const resultElement = document.getElementById("result");
    resultElement.style.display = "none"; // Hide the notification initially

    // Define the API URL
    const apiUrl = "http://localhost:8080/email/send"; // Replace with the actual API endpoint URL

    // Make the API call using the Fetch API
    fetch(apiUrl, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Email sending failed");
            }
        })
        .then(data => {
            // Display a success message
            resultElement.textContent = data;
            resultElement.classList.add("success");
            resultElement.style.display = "block";
            setTimeout(() => {
                resultElement.style.display = "none";
            }, 3000);
        })
        .catch(error => {
            // Display an error message
            resultElement.textContent =error.message;
            resultElement.classList.add("error");
            resultElement.style.display = "block";
            setTimeout(() => {
                resultElement.style.display = "none";
            }, 3000);
        });
});
document.getElementById("email-form").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById("email-input").value;
    const data = { email: email };

    fetch("http://localhost:8080/submit-data", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response from the backend
            document.getElementById("result").textContent = data.message;
        })
        .catch(error => {
            // Handle errors
            console.error("Error:", error);
        });
});



