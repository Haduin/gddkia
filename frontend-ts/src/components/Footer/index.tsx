import { Box, Container, Link, Typography, styled } from '@mui/material';

const FooterWrapper = styled(Container)(
  ({ theme }) => `
    margin-top: ${theme.spacing(4)};
  `
);

function Footer() {
  return (
    <FooterWrapper className="footer-wrapper">
      <Box
        pb={4}
        display={{ xs: 'block', md: 'flex' }}
        alignItems="center"
        textAlign={{ xs: 'center', md: 'left' }}
        justifyContent="space-between"
      >
        <Box>
          <Typography variant="subtitle2" color="secondary" component="span">
            &copy; Dashboard Template&nbsp;
            <Typography
              component={Link}
              variant="subtitle2"
              href="https://www.gov.pl/web/gddkia/generalna-dyrekcja-drog-krajowych-i-autostrad"
              target="_blank"
              underline="hover"
            >
              Gddkia
            </Typography>
          </Typography>
        </Box>
      </Box>
    </FooterWrapper>
  );
}

export default Footer;
