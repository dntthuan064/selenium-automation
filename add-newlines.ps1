Get-ChildItem -Path "src" -Recurse -Filter "*.java" | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    if (-not $content.EndsWith("`n")) {
        $content += "`n"
        Set-Content -Path $_.FullName -Value $content -NoNewline
    }
}

Get-ChildItem -Path "src" -Recurse -Filter "*.properties" | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    if (-not $content.EndsWith("`n")) {
        $content += "`n"
        Set-Content -Path $_.FullName -Value $content -NoNewline
    }
} 