
def lg(n):
	theN = 1
	while (n/2 != 0):
		n /= 2
		theN *= 2

	return theN

def main():
	N = 9
	print 'N =', N
	print 'theN =', lg(N)

if __name__ == '__main__':
	main()