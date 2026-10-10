/* Progressive enhancement only. The page works without JavaScript:
 * pick options, press "Get forecast". This script just removes the extra click. */
(function () {
  var form = document.getElementById('search-form');
  var input = document.getElementById('city');
  var recent = document.getElementById('recent');
  var KEY = 'wx.recent';

  function read() {
    try { return JSON.parse(localStorage.getItem(KEY)) || []; } catch (e) { return []; }
  }
  function write(list) {
    try { localStorage.setItem(KEY, JSON.stringify(list)); } catch (e) { /* storage blocked */ }
  }

  // Remember the city this page resolved to.
  var resolved = document.body.dataset.city;
  if (resolved) {
    var list = read().filter(function (c) { return c.toLowerCase() !== resolved.toLowerCase(); });
    list.unshift(resolved);
    write(list.slice(0, 5));
  }

  // Show recent cities as buttons.
  read().forEach(function (city) {
    var b = document.createElement('button');
    b.type = 'button';
    b.className = 'chip';
    b.textContent = city;
    b.addEventListener('click', function () { input.value = city; form.requestSubmit(); });
    recent.appendChild(b);
  });

  // Changing days or unit re-runs the search straight away.
  form.addEventListener('change', function (e) {
    if (e.target.type === 'radio' && input.value.trim()) form.requestSubmit();
  });

  form.addEventListener('submit', function () { document.body.classList.add('is-loading'); });
  window.addEventListener('pageshow', function () { document.body.classList.remove('is-loading'); });
})();
