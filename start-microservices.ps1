# Starts all MTIT Spring Boot services in separate windows (Windows).
# Wait for them to finish starting, then open http://localhost:8080/

$ErrorActionPreference = "Stop"
$root = $PSScriptRoot

$services = @(
    @{ dir = "enrollment-service"; name = "Enrollment (8081)" },
    @{ dir = "lecturer-service";   name = "Lecturer (8082)" },
    @{ dir = "student-service";    name = "Student (8083)" },
    @{ dir = "course-service";     name = "Course (8084)" },
    @{ dir = "api-gateway";        name = "API Gateway (8080)" }
)

foreach ($s in $services) {
    $path = Join-Path $root $s.dir
    if (-not (Test-Path $path)) {
        Write-Warning "Skip missing: $path"
        continue
    }
    if ($s.dir -eq "api-gateway") {
        Write-Host "Waiting for backends to accept connections..."
        Start-Sleep -Seconds 12
    }
    Write-Host "Starting $($s.name) ..."
    Start-Process powershell -ArgumentList @(
        "-NoExit",
        "-NoProfile",
        "-Command",
        "Set-Location -LiteralPath '$path'; mvn spring-boot:run"
    )
    if ($s.dir -ne "api-gateway") {
        Start-Sleep -Seconds 2
    }
}

Write-Host ""
Write-Host "Public API + Swagger (single port): http://localhost:8080/"
Write-Host "Backends run on 8081-8084; use only :8080 from clients."
