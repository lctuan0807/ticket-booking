# stop containers
docker-compose -f environment/docker-compose-dev.yml -p ticket-booking down
echo "[Ticket-Booking] Containers stopped."