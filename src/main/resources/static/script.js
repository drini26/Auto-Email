
document.getElementById("send-email-button").addEventListener("click", function () {
    const resultElement = document.getElementById("result");
    resultElement.style.display = "none"; // Hide the notification initially

    // Define the API URL
    const apiUrl = "http://localhost:8080/email/send"; 

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
document.getElementById("submit-button").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById("email-input").value;
    const data = { email: email };

    fetch("http://localhost:8080/submit-data", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.includes("application/json")) {
                    return response.json();
                } else {
                    return response.text(); // Parse the response as text
                }
            } else {
                throw new Error("Request failed with status: " + response.status);
            }
        })
        .then(data => {
            if (typeof data === 'string') {
                // The response is plain text
                console.log(data);
                document.getElementById("result").textContent = data;
            } else {
                // The response is JSON
                console.log(data);
                document.getElementById("result").textContent = data.message;
            }
        })
        .catch(error => {
            console.error("Error:", error);
            // Handle other errors
            document.getElementById("result").textContent = "Error: " + error.message;
            document.getElementById("result").classList.add("error"); // Add a CSS class for error styling
            document.getElementById("result").style.display = "block";
        });
});
const showImagesButton = document.getElementById("show-images-button");

// Define getImageUrls function to store image URLs
let imageUrls = [];

async function loadImages() {
    // Remove the event listener
    showImagesButton.removeEventListener("click", loadImages);

    try {
        const response = await fetch("http://localhost:8080/images");
        if (response.ok) {
            imageUrls = await response.json();
            displayImages(imageUrls);
            attachClickHandlers(imageUrls); // Attach click event handlers after displaying images
        } else {
            console.error("Error fetching image URLs");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Add the event listener
showImagesButton.addEventListener("click", loadImages);

function displayImages(imageUrls) {
    const imageContainer = document.getElementById("image-container");
    imageContainer.innerHTML = ''; // Clear previous images

    imageUrls.forEach(url => {
        const imageElement = document.createElement("img");
        imageElement.src = url;
        imageContainer.appendChild(imageElement);
    });
}

function attachClickHandlers(imageUrls) {
    const imageContainer = document.getElementById("image-container");
    const images = imageContainer.getElementsByTagName("img");

    for (let i = 0; i < images.length; i++) {
        images[i].addEventListener("click", () => {
            handleImageSelection(imageUrls[i]);

            // Convert the images collection to an array
            const imagesArray = Array.from(images);

            // Remove "selected" class from all images
            imagesArray.forEach(image => image.classList.remove("selected"));

            // Add "selected" class to the clicked image
            images[i].classList.add("selected");
        });
    }

}



function handleImageSelection(selectedImageUrl) {
    const modal = document.getElementById("image-modal");
    const modalImage = modal.querySelector("img");
    modalImage.src = selectedImageUrl;
    modal.style.display = "block";
    sendSelectedImageUrlToServer(selectedImageUrl);

}

function closeModal() {
    const modal = document.getElementById("image-modal");
    modal.style.display = "none";
}

let modal;
document.addEventListener("DOMContentLoaded", function () {
    // Your code here
    const closeBtn = document.querySelector(".close");
    closeBtn.addEventListener("click", closeModal);
    // ... other code ...
});

function sendSelectedImageUrlToServer(selectedImageUrl) {
    const url = "http://localhost:8080/selectedImage"; // Replace with the actual endpoint on your backend

    const data = {
        selectedImageUrl: selectedImageUrl
    };

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                // Request was successful
            } else {
                // Handle the error
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}
