let updateindex = 0;
function saveOrUpdate() {
    if (document.getElementById('myBtn').innerHTML === 'Save') {
        additem();
    } else {
        update();
    }
}
async function edit(id) {
    const response = await fetch(`http://localhost:9090/getone/${id}`);
    document.getElementById('myBtn').innerHTML = 'update';
    const stock = await response.json();
    document.getElementById('symbol').value = stock.symbol;
    document.getElementById('companyName').value = stock.companyName;
    document.getElementById('currentPrice').value = stock.currentPrice;
    document.getElementById('dates').value = stock.date;
    updateindex = id;
}
async function update() {
    await fetch(`http://localhost:9090/edit/${updateindex}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            symbol: document.getElementById('symbol').value,
            companyName: document.getElementById('companyName').value,
            currentPrice: parseFloat(document.getElementById('currentPrice').value),
            date: new Date(document.getElementById('dates').value)
        })
    });
    document.getElementById('myBtn').innerHTML = 'Save';
    document.getElementById('stockForm').reset();
    location.reload();
};
//  }
async function deleteE(id) {
    const confirmed = confirm("Are you sure you want to delete?");
    if (confirmed) {
        fetch(`http://localhost:9090/delete/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('deleted')
                } else {
                    alert("Failed to delete entity. Please try again.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("An error occurred while trying to delete the entity.");
            });
            location.reload(); 
    }
}

function additem() {
 
    document.getElementById('stockForm').addEventListener('submit', async function (event) {
        event.preventDefault();
        const stockData = {
            symbol: document.getElementById('symbol').value,
            companyName: document.getElementById('companyName').value,
            currentPrice: parseFloat(document.getElementById('currentPrice').value),
            date: document.getElementById('dates').value
        };

        try {
            const response = await fetch('http://localhost:9090/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(stockData)
            });
            if (response.ok) {
                alert('Stock added successfully!');
                document.getElementById('stockForm').reset(); 
                location.reload(); 
            } else {
                alert('Error: Unable to add stock.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('There was an error adding the stock.');
        }
    });

}


async function fetchStockData() {
    try {
        const response = await fetch('http://localhost:9090/details');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const stocks = await response.json();

        const tableBody = document.querySelector('#stockTable tbody');

        stocks.forEach(stock => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${stock.stockId}</td>
                <td>${stock.symbol}</td>
                <td>${stock.companyName}</td>
                <td>${stock.currentPrice}</td>
                <td>${new Date(stock.date).toLocaleDateString()}</td>
                <td><button onclick="deleteE(${stock.stockId})">Delete</button></td>
                <td><button onclick="edit(${stock.stockId})">Edit</button></td>`
            tableBody.appendChild(row);

        });
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

window.onload = fetchStockData;