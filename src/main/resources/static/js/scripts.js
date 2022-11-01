document.getElementById("file").addEventListener("change", (event) => {
    const tmpPath = URL.createObjectURL(event.target.files[0])
    const currentFile = document.getElementById("current_photo-candidate")
    if (currentFile !== null) currentFile.setAttribute("src", tmpPath)
})