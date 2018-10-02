import user from '@/rest/_user';
import cart from '@/rest/_cart';
import order from '@/rest/_order';
import product from '@/rest/_product';

//Exported Class
// We make one large object called 'Rest' by merging all the small objects. 
const Rest = {
  ...user,
  ...cart,
  ...order,
  ...product
}

export default Rest;