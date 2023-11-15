const header=document.querySelector("header");
const footer=document.querySelector("footer");

header.innerHTML = `
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
<div class="container-fluid">
    <a class="navbar-brand">
        <img src="logos/casacasa.png"  width="30" height="40" class="d-inline-block align-top">
    </a>
    <a class="navbar-brand" href="logos/casacasa.png"></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link active" href="/">Inicio</a>
        <a class="nav-link" href="/proveedor/buscador">Buscar</a>
       <!-- <a class="nav-link" href="/panel">Panel de Control</a>-->
       <!-- <a class="nav-link" href="/proveedor/registrar">RegProv</a>-->
        <a class="nav-link" href="/login">Login</a>
    </div>
      </div>
  </div>
</nav>

`;



footer.innerHTML= `
 <div class="texto-pie">
<p>ubicacion - chacras de coria -Mendoza</p>
</div>
<div class="redes-sociales">
<a href="https://www.facebook.com/" target="_blank"><img src="logos/facebook.png" alt="Facebook"></a>
<a href="https://www.instagram.com/" target="_blank"><img src="logos/instagram.png" alt="Instagram"></a>
<a href="https://twitter.com/" target="_blank"><img src="logos/twitter.png" alt="Twitter"></a>
<p></p>
</div>

`;