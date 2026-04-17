@echo off
echo Testing Country API endpoint...
curl -X GET "http://localhost:8080/api/users/countries" -w "\n\n"

echo Testing States API endpoint for country ID 1...
curl -X GET "http://localhost:8080/api/users/states/1" -w "\n\n"

echo Testing Cities API endpoint for state ID 1...
curl -X GET "http://localhost:8080/api/users/cities/1" -w "\n\n"

pause
