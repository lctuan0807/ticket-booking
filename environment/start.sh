# start containers
docker-compose -f environment/docker-compose-dev.yml -p ticket-booking up -d
echo "[Ticket-Booking] Containers started"