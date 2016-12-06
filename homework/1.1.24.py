

def gcd(p, q):
	if 0 == q:
		return p
	r = p%q
	return gcd(q,r)

def main():
	result = gcd(105,24)
	print 'result =' ,result



if __name__ == '__main__':
	main()