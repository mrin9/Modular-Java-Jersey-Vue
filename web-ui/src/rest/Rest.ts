import login from '@/rest/_login';
import cart from '@/rest/_cart';

//Exported Class
const Rest = {
  ...login,
  ...cart
}

export default Rest;