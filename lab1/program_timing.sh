echo -e "Time executed for ASM:"
cd Assembly/bin
time ./matrix_mul
cd ../../

echo -e "\nTime executed for Haskell (interpreter):"
cd Haskell/src
time runghc matrix_mul.hs
echo -e "\nTime executed for Haskell (compiled):"
cd ../bin
time ./matrix_mul
cd ../../

echo -e "\nTime executed for C (compiled):"
cd C/bin
time ./matrix_mul
cd ../../

echo -e "\nTime executed for Java (compiled):"
cd Java/bin
time java Main
cd ../../

echo -e "\nTime executed for python:"
cd Python/src
time python matrix_mul.py
cd ../../

