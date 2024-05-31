rootProject.name = "kotlin-ktor-starter"

include(
    "applications:basic-server",
    "applications:data-analyzer-server",
    "applications:data-collector-server",

    "components:data-collector",
    "components:data-analyzer",

    "support:logging-support",
    "support:workflow-support"
)
