import user from '@/rest/_user';
import cart from '@/rest/_cart';
import order from '@/rest/_order';

//Exported Class
const Rest = {
  ...user,
  ...cart,
  ...order
}

export default Rest;