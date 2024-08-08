# Users Starter (w/ event sourcing)

## Task List

- [x] use log4j
- [x] setup liquibase
- [x] add ability to create user
- [x] add ability to list users
- [ ] setup eventstoredb in docker
- [ ] copy eventstoredb code

---

- [x] list users using htmx
- [x] list users using svelte

## htmx Notes

- we need some kind of templating engine
    - I tried the ktor html dsl and did not enjoy the experience. I think with htmx that we would want to return html
      fragments and the ktor dsl is "type safe" and only allows us to construct whole documents. I may be possible, but
      would need further research.
    - I tried Velocity and struggled (but I've used in the past with success)
    - I finally had some success with FreeMarker
- The experience is... interesting.
    - I'm probably not doing it right, but it feels like there is some html in an ui directory and then there is some
      html that the application returns. The reason it feels strange is that I have to know where to look for each
      specific piece since it's not all in one location.
    - With svelte, I had to rebuild the ui portion on every change (since I used the static adapater). With htmx, I had
      to rebuild the project since the html fragments were returned by the server. The svelte one was faster/easier, but
      also seems more heavy weight (thought not as heavy weight as React)..
