import login from '@/rest/_login';
import cart from '@/rest/_cart';
import order from '@/rest/_order';

//Exported Class
const Rest = {
  ...login,
  ...cart,
  ...order
}

export default Rest;