
#arr = [1,1,2,2,3,4,4,4];
def histogram(arr, m):
	h = []

	for i in range(0, len(arr)):
		h.append(0)

	print 'before h =', h

	for i in range(0, len(arr)):
		if arr[i] < m:
			h[arr[i]] = h[arr[i]] + 1
	return h
		

def main():
	arr = [1,1,2,2,3,4,4,4]
	print 'arr =', arr
	h = histogram(arr, 10)
	print 'after h =', h

if __name__ == '__main__':
	main()