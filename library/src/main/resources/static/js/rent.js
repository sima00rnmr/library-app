let scannedCode = "";

function startScan() {
    const scanner = new Html5Qrcode("reader");

    scanner.start(
        { facingMode: "environment" },
        { fps: 10, qrbox: 250 },
        (decodedText) => {
            scannedCode = decodedText;
            document.getElementById("result").innerText = decodedText;
            scanner.stop();
        }
    );
}

function rent() {

    const userId = document.getElementById("userId").value;

    if (!scannedCode) {
        alert("QRコードを読み取ってください");
        return;
    }

    if (!userId) {
        alert("ユーザーIDを入力してください");
        return;
    }

    fetch('/api/rentals', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            qrCode: scannedCode,
            userId: userId
        })
    })
    .then(res => res.text())
    .then(data => alert(data))
    .catch(err => alert("エラー：" + err));
}