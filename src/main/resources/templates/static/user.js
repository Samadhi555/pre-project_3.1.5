const userUrl = 'http://localhost:8080/page';


function fillUserData(user) {
    const userDataBody = document.getElementById('userDataBody');
    const row = document.createElement('tr');

    // Замените user.id, user.firstname и т.д. на соответствующие свойства вашего объекта пользователя
    row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name.substring(5)).join(' ')}</td>
        `;

    userDataBody.appendChild(row);
}

// Используем Fetch API для получения данных пользователя
fetch('/page')
    .then(response => response.json())
    .then(data => {
        // Заполнение данных в заголовке и в таблице
        document.querySelector('.navbar-brand.font-weight-bold').textContent = data.email;
        document.querySelector('.navbar-brand:nth-child(2)').textContent = 'with roles: ' + data.roles.join(' ');
        fillUserData(data);
    })
    .catch(error => {
        console.error('Error fetching user data:', error);
    });