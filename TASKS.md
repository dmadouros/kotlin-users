# Users Starter (w/ event sourcing)

## Task List

- [x] use log4j
- [x] setup liquibase
- [x] add ability to create user
- [x] add ability to list users
- [ ] setup eventstoredb in docker
- [ ] copy eventstoredb code

---
- [ ] list users using htmx
- [x] list users using svelte

## Svelte Notes

- You must use SvelteKit and not just Svelte. Svelte doesn't have a native way to fetch data.
- In order to serve the pages from the ktor app, you must:

  1\. serve staticFiles in routes

  ```
  staticFiles("/", File("ui/admin-console/build"))
  staticFiles("/assets", File("ui/admin-console/build/assets"))
  ```

  2\. Use the `@sveltejs/adapter-static` adapter and set the `fallback` option to `index.html`.

  3\. You have to `npm run build` to see changes in the browser.

  4\. We'll have to figure out how to do all this in Docker.

