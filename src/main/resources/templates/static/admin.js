const url = 'http://localhost:8080/api/admin';

function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        });
}

function loadTable(listAllUsers) {
    let res = ``;

    for (let user of listAllUsers) {
        res +=
            `<tr id="row" >
                <td>${user.id}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
               <td>${user.roles.join(' ')}</td>
    
               <td>
                    <button id="button-edit" class="btn btn-sm btn-primary" type="button"
                    data-bs-toggle="modal" href="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-sm btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`;
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;
}

function newUserTab() {
    document.getElementById('newUserForm').addEventListener('submit', (e) => {
        e.preventDefault();

        const formData = new FormData(document.getElementById('newUserForm'));
        const roles = formData.getAll('roles');

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                firstname: formData.get('nameNew'),
                lastname: formData.get('lastNameNew'),
                age: formData.get('ageNew'),
                email: formData.get('emailNew'),
                password: formData.get('passwordNew'),
                roles: roles
            })
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById('newUserForm').reset();
                    document.getElementById('userTable-tab').click();
                    getAllUsers();
                }
            })
            .catch(error => console.error('Error creating new user:', error));
    });
}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click());
}

function editModal(id) {
    fetch(`${url}/${id}`)
        .then(res => res.json())
        .then(user => {
            document.getElementById('editId').value = user.id;
            document.getElementById('editName').value = user.firstname;
            document.getElementById('editLastName').value = user.lastname;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editEmail').value = user.email;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('editRole').value = user.roleToString;
        })
        .catch(error => console.error('Error fetching user for edit:', error));
}

async function editUser() {
    const idValue = document.getElementById('editId').value;
    const formData = new FormData(document.getElementById('editForm'));
    const roles = formData.getAll('roles');

    const user = {
        id: idValue,
        firstname: formData.get('editName'),
        lastname: formData.get('editLastName'),
        age: formData.get('editAge'),
        email: formData.get('editEmail'),
        password: formData.get('editPassword'),
        roles: roles
    };

    await fetch(`${url}/${idValue}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });

    closeModal();
    getAllUsers();
}

function deleteModal(id) {
    fetch(`${url}/${id}`)
        .then(res => res.json())
        .then(user => {
            document.getElementById('deleteId').value = user.id;
            document.getElementById('deleteName').value = user.firstname;
            document.getElementById('deleteLastName').value = user.lastname;
            document.getElementById('deleteAge').value = user.age;
            document.getElementById('deleteEmail').value = user.email;
            document.getElementById('deleteRoles').value = user.rolesToString;
        })
        .catch(error => console.error('Error fetching user for delete:', error));
}

async function deleteUser() {
    const id = document.getElementById('deleteId').value;

    await fetch(`${url}/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    });

    closeModal();
    getAllUsers();
}

getAllUsers();
newUserTab();
