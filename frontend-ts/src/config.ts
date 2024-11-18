// ==============================|| THEME CONFIG  ||============================== //

// Define a type for the configuration settings
interface Config {
  defaultPath: string;
  fontFamily: string;
  i18n: string;
  miniDrawer: boolean;
  container: boolean;
  mode: 'light' | 'dark';
  presetColor: 'default' | 'theme1' | 'theme2' | 'theme3' | 'theme4'; // Presuming possible values
  themeDirection: 'ltr' | 'rtl';
  backend: string;
}

// Define the configuration object with type safety
const config: Config = {
  defaultPath: '/',
  fontFamily: `'Public Sans', sans-serif`,
  i18n: 'en',
  miniDrawer: false,
  container: true,
  mode: 'light',
  presetColor: 'default',
  themeDirection: 'ltr',
  backend: 'http://localhost:8080'
};

export default config;

// Define and export additional constants with appropriate types
export const drawerWidth: number = 260;

export const twitterColor: string = '#1DA1F2';
export const facebookColor: string = '#3b5998';
export const linkedInColor: string = '#0e76a8';
