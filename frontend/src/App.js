import './App.css';
import {Box, Container, Typography} from "@mui/material";


function App() {
    return (
        <Container >
            <Box component="span" sx={{display: "block", p:10, m:1,}}>
                <Typography>Home Page</Typography>
            </Box>
        </Container>
    );
}

export default App;
