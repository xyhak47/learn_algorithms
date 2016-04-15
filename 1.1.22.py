

def BinarySearch(key, arr):
	begin = 0
	end = len(arr) - 1
	middle = begin + (end - begin)/2

	if key > arr[middle]:
		begin = middle + 1
	elif key < arr[middle]:
		end = middle - 1
	else:
		return middle

	return BinarySearch(key, arr[begin, end + 1]) 


if __name__ == '__main__':
	arr = [1,2,5,8,9]
	key = 5
	result = BinarySearch(key, arr)
	print 'result =', result