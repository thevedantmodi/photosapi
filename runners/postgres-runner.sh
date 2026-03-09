docker run --name photos-pg -e POSTGRES_PASSWORD=helloworld1234 \
  -e POSTGRES_DB=photos_db -p 5432:5432 -d postgres:16