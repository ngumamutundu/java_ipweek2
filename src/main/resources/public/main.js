document.addEventListener('DOMContentLoaded', function () {
  const templateSource = document.getElementById('template').innerHTML;
  const template = Handlebars.compile(templateSource);
  const BASE_URL = "http://localhost:4567";
  const app = document.getElementById('app');

  function getHeroes() {
    return fetch(`${BASE_URL}/heroes`)
      .then(response => response.json());
  }

  function getHeroById(id) {
    return fetch(`${BASE_URL}/heroes/${id}`)
      .then(response => response.json());
  }

  function addHero(hero) {
    return fetch(`${BASE_URL}/heroes`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(hero)
    }).then(response => response.json());
  }

  function render(data) {
    app.innerHTML = template(data);
  }

  function fetchData() {
    fetch('/heroes')
      .then(response => response.json())
      .then(data => {
        const heroes = data;
        fetch('/squads')
          .then(response => response.json())
          .then(data => {
            const squads = data;
            render({ heroes, squads });
          });
      });
  }

  fetchData();
});
