<#macro noauthentication title="Welcome">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name=viewport content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/static/styles/reset.css">
        <link rel="stylesheet" href="/static/styles/style.css">
        <link rel="icon" type="image/svg" href="/static/images/favicon.svg">
        <title>Kotlin ktor starter</title>
    </head>
    <body>
    <header>
        <div class="container">
            <h1>Kotlin ktor starter</h1>
        </div>
    </header>
    <section class="callout">
        <div class="container">
            an <span class="branded">AppContinuum[]</span> application with background workers.
        </div>
    </section>
    <main>
        <#nested>
    </main>
    <footer>
        <div class="container">
            <script>document.write("©" + new Date().getFullYear());</script>
            Initial Capacity, Inc. All rights reserved.
        </div>
    </footer>
    </body>
    </html>
</#macro>