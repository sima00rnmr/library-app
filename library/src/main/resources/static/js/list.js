window.onload = function () {
    fetch('/api/rentals/active')
        .then(res => res.json())
        .then(data => {

            const tbody = document.getElementById("list");

            data.forEach(item => {
                const row = `
                    <tr>
                        <td>${item.bookTitle}</td>
                        <td>${item.qrCode}</td>
                        <td>${item.userId}</td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });
        });
};