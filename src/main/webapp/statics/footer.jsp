<div class="light-button">
    <input id="lightSwitch" type="checkbox" class="btn-check" checked autocomplete="off">
    <label id="lightSwitch-label" class="" for="lightSwitch"><i class="bi bi-sun"
                                                                style="font-size: 2.0rem;"></i></label>
</div>

<script>
    function onToggleMode() {
        let lightSwitch = document.getElementById('lightSwitch');
        if (lightSwitch.checked) {
            setMode('dark');
        } else {
            setMode('light');
        }
        localStorage.setItem('lightSwitch', lightSwitch.checked);
    }

    function setMode(mode) {
        var body = document.querySelector('body');
        body.setAttribute('data-bs-theme', mode);
        var label = document.querySelector('#lightSwitch-label');
        if (mode == 'light') {
            label.innerHTML = '<i class="bi bi-sun" style="font-size: 2.0rem;"></i>';
        } else {
            label.innerHTML = '<i class="bi bi-moon" style="font-size: 2.0rem;"></i>';
        }
    }


    function getSystemDefaultTheme() {
        const darkThemeMq = window.matchMedia('(prefers-color-scheme: dark)');
        if (darkThemeMq.matches) {
            return 'dark';
        }
        return 'light';
    }


    function setup() {
        let lightSwitch = document.getElementById('lightSwitch');
        var settings = localStorage.getItem('lightSwitch');

        if (settings == null) {
            setMode(getSystemDefaultTheme());
        }

        if (settings == 'true') {
            lightSwitch.checked = true;
            setMode('dark');
        } else {
            setMode('light');
        }

        lightSwitch.addEventListener('change', onToggleMode);
    }


    window.onload = function () {
        setup();
    };


</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
</body>

</html>