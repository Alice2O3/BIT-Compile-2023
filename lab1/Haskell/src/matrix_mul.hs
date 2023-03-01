{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use camelCase" #-}
{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module Main where

nthElement :: [a] -> Int -> a
nthElement list n = list !! n

listSlice :: [a] -> Int -> Int -> [a]
listSlice lst l r = take (r - l + 1) (drop l lst)

split :: String -> Char -> [String]
split str d = case dropWhile (== d) str of
                "" -> []
                s1 -> w : split s2 d
                    where (w, s2) = break (== d) s1

trans :: [[a]] -> [[a]]
trans [] = []
trans ([]:xss) = trans xss
trans ((x:xs):xss) = (x : fmap head' xss) : trans (xs : fmap tail' xss)
    where head' (x:_) = x
          tail' (_:xs) = xs

str_to_vector :: String -> [Int]
str_to_vector str = map read (split str ' ') :: [Int]

dot :: Num a => [a] -> [a] -> a
dot v1 v2 = sum $ zipWith (*) v1 v2

matrixdot :: Num b => [[b]] -> [b] -> [b]
matrixdot ma v = map (dot v) ma

matrixmul :: Num b => [[b]] -> [[b]] -> [[b]]
matrixmul ma mb = trans (map (matrixdot ma) (trans mb))

print2DArray :: [[Int]] -> IO ()
print2DArray arr = mapM_ (putStrLn . unwords . map show) arr

main = do
    let file = "../../data.txt"
    contents <- readFile file
    let file_text = split contents '\n'
    let arr_3 = map read (split (nthElement file_text 0) ' ') :: [Int]
    let n = nthElement arr_3 0
    let m = nthElement arr_3 1
    let arr_4 = map read (split (nthElement file_text (n+1)) ' ') :: [Int]
    let t = nthElement arr_4 1
    let ma = map str_to_vector (listSlice file_text 1 n)
    let mb = map str_to_vector (listSlice file_text (n+2) (n+m+1))
    let mc = matrixmul ma mb
    print (nthElement (nthElement mc 0) 0)
