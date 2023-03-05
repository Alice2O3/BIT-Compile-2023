echo -e "Generating data for n=100, m=100, t=100"
cd data_gen
python data_gen.py --n 100 --m 100 --t 100
cd ../
./program_timing.sh

echo -e "----------------"
echo -e "Generating data for n=200, m=200, t=200"
cd data_gen
python data_gen.py --n 200 --m 200 --t 200
cd ../
./program_timing.sh

echo -e "----------------"
echo -e "Generating data for n=300, m=400, t=500"
cd data_gen
python data_gen.py --n 300 --m 400 --t 500
cd ../
./program_timing.sh

echo -e "----------------"
echo -e "Generating data for n=500, m=600, t=700"
cd data_gen
python data_gen.py --n 500 --m 600 --t 700
cd ../
./program_timing.sh

echo -e "----------------"
echo -e "Generating data for n=800, m=900, t=1000"
cd data_gen
python data_gen.py --n 800 --m 900 --t 1000
cd ../
./program_timing.sh

echo -e "----------------"
echo -e "Timing finished!"

