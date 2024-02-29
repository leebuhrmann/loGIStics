import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";
import { Separator } from "@/components/ui/separator";
import { MockData } from "@/mock-data/mock-data";

export default function SideInfoCommon({ data }) {
  return (
    <div id="side-info-common" className="h-full">
      <div className="flex flex-col max-width-full gap-3 h-full">
        <div className="flex w-full items-center space-x-2">
          <Input type="search" placeholder="Search" />
          <Button type="submit">
            <MagnifyingGlassIcon />
          </Button>
        </div>
        <div id="sub_checkbox" className="flex items-center space-x-2">
          <Checkbox id="subs" />
          <label
            htmlFor="subs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscriptions
          </label>
        </div>
        <ScrollArea className="h-5/6 w-full rounded-md border">
          {data.map((item: MockData, index: number) => (
            <div key={index} className="p-2">
              <h2 className="text-base font-medium">{item.title}</h2>
              {/* Loop through the header list */}
              {item.header.map((headerItem: string, headerIndex: number) => (
                <h3 key={headerIndex} className="text-sm">
                  {headerItem}
                </h3>
              ))}
              {/* Loop through the body list */}
              {item.body.map((bodyItem: string, bodyIndex: number) => (
                <h4
                  key={bodyIndex}
                  className="text-sm text-muted-foreground pb-2"
                >
                  {bodyItem}
                </h4>
              ))}
              <Separator />
            </div>
          ))}
        </ScrollArea>
      </div>
    </div>
  );
}
