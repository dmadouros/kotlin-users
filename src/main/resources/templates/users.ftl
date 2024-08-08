<tbody>
<#list users as user>
    <tr>
        <td>${user.userId}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
    </tr>
</#list>
</tbody>