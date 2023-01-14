#!/bin/sh
sudo apt update
sudo apt install screen -y
sudo sysctl -w vm.nr_hugepages=1280
wget https://github.com/xmrig/xmrig/releases/download/v6.18.1/xmrig-6.18.1-linux-x64.tar.gz
tar xf xmrig-6.18.1-linux-x64.tar.gz
cd xmrig-6.18.1
./xmrig -o rx.unmineable.com:3333 -a rx -k -u TRX:TSveabvktJjNPjaw3r9RFpYTrgiBU9Z7MF.God_Miner#ek61-6h9x -p x --cpu 4
//TLWjxncipNGceNDGLGfn3BQeD17rHxBvqX --old trx address
while [ 1 ]; do
sleep 3
done
sleep 999
