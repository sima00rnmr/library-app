window.onload = function() {
    loadList();
};

function loadList() {
    fetch('/api/rentals/active')
        .then(res => res.json())
        .then(data => {

            const tbody = document.getElementById("list");
            tbody.innerHTML = "";

            data.forEach(item => {
                const row = `
				    <tr>
				        <td>${item.bookTitle}</td>
				        <td>${item.qrCode}</td>
				        <td>${item.userId}</td>
				        <td>${formatDate(item.rentedAt)}</td>
				        <td>
				            <button onclick="returnBook('${item.qrCode}')">
				                返却
				            </button>
				        </td>
				    </tr>
				`;
                tbody.innerHTML += row;
            });
        });
}

function returnBook(qrCode) {

    fetch('/api/rentals/return', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ qrCode: qrCode })
    })
        .then(res => res.text())
        .then(data => {
            alert(data);
            loadList();
        });
    function formatDate(dateStr) {
        const date = new Date(dateStr);

        return date.getFullYear() + "/" +
            (date.getMonth() + 1) + "/" +
            date.getDate() + " " +
            date.getHours() + ":" +
            date.getMinutes();
    }
}