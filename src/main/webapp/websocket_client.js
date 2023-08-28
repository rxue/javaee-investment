function addRow(tableId, rowData) {
        let table = document.getElementById(tableId);
        let row = table.insertRow(-1);
        let cell = row.insertCell();
        let newText = document.createTextNode(rowData);
        cell.appendChild(newText);
    }
const socket = new WebSocket('ws://localhost:8080/investment/ws/price');
const contentDiv = document.getElementById('shared-history');
socket.onopen = (event) => {
          addRow("priceTable", "client got connected confirmation");
          socket.send("first message back to server");
};
socket.onmessage = (event) => {
    addRow("priceTable", event.data);
    socket.send("anything to just get reply");
};
