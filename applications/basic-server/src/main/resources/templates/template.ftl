<#macro noauthentication title="Welcome">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name=viewport content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#ff835c">

        <title>Colorado Collective</title>
        <link rel="stylesheet" href="/style/reset.css">
        <link rel="stylesheet" href="/style/site.css">
    </head>
    <body>
    <header>
        <div class="container">
            <h1>Kotlin Ktor starter</h1>

            <p>
                Basic application with background workers using Kotlin and Ktor
            </p>
            <p>
                <a href="/">home</a>
                <a href="/authenticated">authenticated</a>
                <a href="/authorized">authorized</a>
            </p>
        </div>
    </header>

    <#nested />

    <section>
        <div class="container">
            <p>Request headers</p>
            <pre>
                <code>
                    <#list headers as key, value>
${key}: ${value}
                    </#list>
                </code>
            </pre>
        </div>
    </section>

    </body>
    </html>
</#macro>