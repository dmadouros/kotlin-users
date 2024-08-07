<script>
    export let data;
</script>

<h1>Users</h1>
<table border="1" cellpadding="10" cellspacing="1">
    <thead>
        <tr>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
    </thead>
    <tbody>
    {#each data.item as {userId, firstName, lastName}}
        <tr>
            <td>{userId}</td>
            <td>{firstName}</td>
            <td>{lastName}</td>
        </tr>
    {/each}
    </tbody>
</table>

<style>
    th {
        font-weight: bold;
        color: white;
        background-color: black;
        text-align: left;
    }
</style>
