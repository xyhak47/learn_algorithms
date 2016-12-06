
import math

def lnX(n):
	if n == 1:
		return 0
	return math.log(n) + lnX(n-1)

if __name__ == '__main__':
	#ln(ab) = lna + lnb
	print 'result =', lnX(100)