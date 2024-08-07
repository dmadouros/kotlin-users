export async function load({fetch}) {
    const res = await fetch(`/api/users`);
    const item = await res.json();
    return {item};
}