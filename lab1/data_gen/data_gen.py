import os
import random
import click

@click.command()
@click.option('--n', help='Ma rows', type=click.IntRange(min=0), default = 100)
@click.option('--m', help='Ma columns/Mb rows', type=click.IntRange(min=0), default = 100)
@click.option('--t', help='Mb columns', type=click.IntRange(min=0), default = 100)
def main(n, m, t):
    with open('../data.txt', 'w') as f:
        f.write(f"{n} {m}\n")
        for i in range(n):
            for j in range(m):
                Aij = random.randint(100, 1000)
                f.write(f"{Aij}")
                if j < m-1:
                    f.write(" ")
            f.write("\n")
        f.write(f"{m} {t}\n")
        for i in range(m):
            for j in range(t):
                Bij = random.randint(100, 1000)
                f.write(f"{Bij}")
                if j < t-1:
                    f.write(" ")
            f.write("\n")

if __name__ == "__main__":
    main()
