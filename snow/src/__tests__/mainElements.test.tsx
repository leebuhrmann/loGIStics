import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";


describe("Main elements are rendered", () => {
    beforeEach(() => {
        user.setup();
        render(<Home />);
    });

    test("user account nav bar is rendered", () => {
        const navigationElement = screen.getByRole("listitem");
        expect(navigationElement).toBeInTheDocument();
    });

    test("map is rendered", () => {
        const mockMapComponent = screen.getByTestId("mockMapComponent");
        expect(mockMapComponent).toBeInTheDocument();
    });
});